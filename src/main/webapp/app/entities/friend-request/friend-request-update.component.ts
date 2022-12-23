import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import { IFriendRequest, FriendRequest } from '@/shared/model/friend-request.model';
import FriendRequestService from './friend-request.service';

const validations: any = {
  friendRequest: {},
};

@Component({
  validations,
})
export default class FriendRequestUpdate extends Vue {
  @Inject('friendRequestService') private friendRequestService: () => FriendRequestService;
  @Inject('alertService') private alertService: () => AlertService;

  public friendRequest: IFriendRequest = new FriendRequest();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.friendRequestId) {
        vm.retrieveFriendRequest(to.params.friendRequestId);
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
    if (this.friendRequest.id) {
      this.friendRequestService()
        .update(this.friendRequest)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.friendRequest.updated', { param: param.id });
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
      this.friendRequestService()
        .create(this.friendRequest)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.friendRequest.created', { param: param.id });
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

  public retrieveFriendRequest(friendRequestId): void {
    this.friendRequestService()
      .find(friendRequestId)
      .then(res => {
        this.friendRequest = res;
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
  }
}
