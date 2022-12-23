import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFriendRequest } from '@/shared/model/friend-request.model';
import FriendRequestService from './friend-request.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class FriendRequestDetails extends Vue {
  @Inject('friendRequestService') private friendRequestService: () => FriendRequestService;
  @Inject('alertService') private alertService: () => AlertService;

  public friendRequest: IFriendRequest = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.friendRequestId) {
        vm.retrieveFriendRequest(to.params.friendRequestId);
      }
    });
  }

  public retrieveFriendRequest(friendRequestId) {
    this.friendRequestService()
      .find(friendRequestId)
      .then(res => {
        this.friendRequest = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
