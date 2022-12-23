import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import TicketService from '@/entities/ticket/ticket.service';
import { ITicket } from '@/shared/model/ticket.model';

import PrivateChatService from '@/entities/private-chat/private-chat.service';
import { IPrivateChat } from '@/shared/model/private-chat.model';

import PublicChatService from '@/entities/public-chat/public-chat.service';
import { IPublicChat } from '@/shared/model/public-chat.model';

import { IMessage, Message } from '@/shared/model/message.model';
import MessageService from './message.service';

const validations: any = {
  message: {
    message: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class MessageUpdate extends Vue {
  @Inject('messageService') private messageService: () => MessageService;
  @Inject('alertService') private alertService: () => AlertService;

  public message: IMessage = new Message();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('ticketService') private ticketService: () => TicketService;

  public tickets: ITicket[] = [];

  @Inject('privateChatService') private privateChatService: () => PrivateChatService;

  public privateChats: IPrivateChat[] = [];

  @Inject('publicChatService') private publicChatService: () => PublicChatService;

  public publicChats: IPublicChat[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.messageId) {
        vm.retrieveMessage(to.params.messageId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.message.id) {
      this.messageService()
        .update(this.message)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.message.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.messageService()
        .create(this.message)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.message.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveMessage(messageId): void {
    this.messageService()
      .find(messageId)
      .then(res => {
        this.message = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.ticketService()
      .retrieve()
      .then(res => {
        this.tickets = res.data;
      });
    this.privateChatService()
      .retrieve()
      .then(res => {
        this.privateChats = res.data;
      });
    this.publicChatService()
      .retrieve()
      .then(res => {
        this.publicChats = res.data;
      });
  }
}
