import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPublicChat } from '@/shared/model/public-chat.model';

import PublicChatService from './public-chat.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PublicChat extends Vue {
  @Inject('publicChatService') private publicChatService: () => PublicChatService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public publicChats: IPublicChat[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPublicChats();
  }

  public clear(): void {
    this.retrieveAllPublicChats();
  }

  public retrieveAllPublicChats(): void {
    this.isFetching = true;
    this.publicChatService()
      .retrieve()
      .then(
        res => {
          this.publicChats = res.data;
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

  public prepareRemove(instance: IPublicChat): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePublicChat(): void {
    this.publicChatService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.publicChat.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPublicChats();
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
