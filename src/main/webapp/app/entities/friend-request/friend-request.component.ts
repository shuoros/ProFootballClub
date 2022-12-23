import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFriendRequest } from '@/shared/model/friend-request.model';

import FriendRequestService from './friend-request.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class FriendRequest extends Vue {
  @Inject('friendRequestService') private friendRequestService: () => FriendRequestService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public friendRequests: IFriendRequest[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFriendRequests();
  }

  public clear(): void {
    this.retrieveAllFriendRequests();
  }

  public retrieveAllFriendRequests(): void {
    this.isFetching = true;
    this.friendRequestService()
      .retrieve()
      .then(
        res => {
          this.friendRequests = res.data;
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

  public prepareRemove(instance: IFriendRequest): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFriendRequest(): void {
    this.friendRequestService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.friendRequest.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFriendRequests();
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
