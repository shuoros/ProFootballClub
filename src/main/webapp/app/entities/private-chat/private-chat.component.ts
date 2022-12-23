import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPrivateChat } from '@/shared/model/private-chat.model';

import PrivateChatService from './private-chat.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PrivateChat extends Vue {
  @Inject('privateChatService') private privateChatService: () => PrivateChatService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public privateChats: IPrivateChat[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPrivateChats();
  }

  public clear(): void {
    this.retrieveAllPrivateChats();
  }

  public retrieveAllPrivateChats(): void {
    this.isFetching = true;
    this.privateChatService()
      .retrieve()
      .then(
        res => {
          this.privateChats = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IPrivateChat): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePrivateChat(): void {
    this.privateChatService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.privateChat.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPrivateChats();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
