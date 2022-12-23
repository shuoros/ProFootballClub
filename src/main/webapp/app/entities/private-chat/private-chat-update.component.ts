import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import LeagueService from '@/entities/league/league.service';
import { ILeague } from '@/shared/model/league.model';

import CupService from '@/entities/cup/cup.service';
import { ICup } from '@/shared/model/cup.model';

import MessageService from '@/entities/message/message.service';
import { IMessage } from '@/shared/model/message.model';

import { IPrivateChat, PrivateChat } from '@/shared/model/private-chat.model';
import PrivateChatService from './private-chat.service';

const validations: any = {
  privateChat: {},
};

@Component({
  validations,
})
export default class PrivateChatUpdate extends Vue {
  @Inject('privateChatService') private privateChatService: () => PrivateChatService;
  @Inject('alertService') private alertService: () => AlertService;

  public privateChat: IPrivateChat = new PrivateChat();

  @Inject('leagueService') private leagueService: () => LeagueService;

  public leagues: ILeague[] = [];

  @Inject('cupService') private cupService: () => CupService;

  public cups: ICup[] = [];

  @Inject('messageService') private messageService: () => MessageService;

  public messages: IMessage[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.privateChatId) {
        vm.retrievePrivateChat(to.params.privateChatId);
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
    if (this.privateChat.id) {
      this.privateChatService()
        .update(this.privateChat)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.privateChat.updated', { param: param.id });
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
      this.privateChatService()
        .create(this.privateChat)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.privateChat.created', { param: param.id });
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

  public retrievePrivateChat(privateChatId): void {
    this.privateChatService()
      .find(privateChatId)
      .then(res => {
        this.privateChat = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.leagueService()
      .retrieve()
      .then(res => {
        this.leagues = res.data;
      });
    this.cupService()
      .retrieve()
      .then(res => {
        this.cups = res.data;
      });
    this.messageService()
      .retrieve()
      .then(res => {
        this.messages = res.data;
      });
  }
}
