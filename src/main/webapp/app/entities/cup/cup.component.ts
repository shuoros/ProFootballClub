import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICup } from '@/shared/model/cup.model';

import CupService from './cup.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Cup extends Vue {
  @Inject('cupService') private cupService: () => CupService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public cups: ICup[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCups();
  }

  public clear(): void {
    this.retrieveAllCups();
  }

  public retrieveAllCups(): void {
    this.isFetching = true;
    this.cupService()
      .retrieve()
      .then(
        res => {
          this.cups = res.data;
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

  public prepareRemove(instance: ICup): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCup(): void {
    this.cupService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.cup.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCups();
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
