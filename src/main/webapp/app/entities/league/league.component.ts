import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ILeague } from '@/shared/model/league.model';

import LeagueService from './league.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class League extends Vue {
  @Inject('leagueService') private leagueService: () => LeagueService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public leagues: ILeague[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllLeagues();
  }

  public clear(): void {
    this.retrieveAllLeagues();
  }

  public retrieveAllLeagues(): void {
    this.isFetching = true;
    this.leagueService()
      .retrieve()
      .then(
        res => {
          this.leagues = res.data;
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

  public prepareRemove(instance: ILeague): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeLeague(): void {
    this.leagueService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.league.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllLeagues();
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
