import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStadium } from '@/shared/model/stadium.model';

import StadiumService from './stadium.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Stadium extends Vue {
  @Inject('stadiumService') private stadiumService: () => StadiumService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public stadiums: IStadium[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStadiums();
  }

  public clear(): void {
    this.retrieveAllStadiums();
  }

  public retrieveAllStadiums(): void {
    this.isFetching = true;
    this.stadiumService()
      .retrieve()
      .then(
        res => {
          this.stadiums = res.data;
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

  public prepareRemove(instance: IStadium): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStadium(): void {
    this.stadiumService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.stadium.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllStadiums();
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
