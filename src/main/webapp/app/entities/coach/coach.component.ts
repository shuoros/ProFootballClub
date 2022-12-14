import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICoach } from '@/shared/model/coach.model';

import CoachService from './coach.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Coach extends Vue {
  @Inject('coachService') private coachService: () => CoachService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public coaches: ICoach[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCoachs();
  }

  public clear(): void {
    this.retrieveAllCoachs();
  }

  public retrieveAllCoachs(): void {
    this.isFetching = true;
    this.coachService()
      .retrieve()
      .then(
        res => {
          this.coaches = res.data;
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

  public prepareRemove(instance: ICoach): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCoach(): void {
    this.coachService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.coach.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCoachs();
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
