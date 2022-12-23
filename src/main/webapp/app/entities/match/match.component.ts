import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMatch } from '@/shared/model/match.model';

import MatchService from './match.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Match extends Vue {
  @Inject('matchService') private matchService: () => MatchService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public matches: IMatch[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMatchs();
  }

  public clear(): void {
    this.retrieveAllMatchs();
  }

  public retrieveAllMatchs(): void {
    this.isFetching = true;
    this.matchService()
      .retrieve()
      .then(
        res => {
          this.matches = res.data;
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

  public prepareRemove(instance: IMatch): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMatch(): void {
    this.matchService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.match.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMatchs();
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
