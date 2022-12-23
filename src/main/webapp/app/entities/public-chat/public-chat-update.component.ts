import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import MessageService from '@/entities/message/message.service';
import { IMessage } from '@/shared/model/message.model';

import { IPublicChat, PublicChat } from '@/shared/model/public-chat.model';
import PublicChatService from './public-chat.service';

const validations: any = {
  publicChat: {},
};

@Component({
  validations,
})
export default class PublicChatUpdate extends Vue {
  @Inject('publicChatService') private publicChatService: () => PublicChatService;
  @Inject('alertService') private alertService: () => AlertService;

  public publicChat: IPublicChat = new PublicChat();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('messageService') private messageService: () => MessageService;

  public messages: IMessage[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicChatId) {
        vm.retrievePublicChat(to.params.publicChatId);
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
    if (this.publicChat.id) {
      this.publicChatService()
        .update(this.publicChat)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.publicChat.updated', { param: param.id });
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
      this.publicChatService()
        .create(this.publicChat)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.publicChat.created', { param: param.id });
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

  public retrievePublicChat(publicChatId): void {
    this.publicChatService()
      .find(publicChatId)
      .then(res => {
        this.publicChat = res;
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
    this.messageService()
      .retrieve()
      .then(res => {
        this.messages = res.data;
      });
  }
}
