import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITrain } from '@/shared/model/train.model';

import TrainService from './train.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Train extends Vue {
  @Inject('trainService') private trainService: () => TrainService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public trains: ITrain[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTrains();
  }

  public clear(): void {
    this.retrieveAllTrains();
  }

  public retrieveAllTrains(): void {
    this.isFetching = true;
    this.trainService()
      .retrieve()
      .then(
        res => {
          this.trains = res.data;
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

  public prepareRemove(instance: ITrain): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTrain(): void {
    this.trainService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.train.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTrains();
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
