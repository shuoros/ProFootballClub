import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ILeagueBoard } from '@/shared/model/league-board.model';

import LeagueBoardService from './league-board.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class LeagueBoard extends Vue {
  @Inject('leagueBoardService') private leagueBoardService: () => LeagueBoardService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public leagueBoards: ILeagueBoard[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllLeagueBoards();
  }

  public clear(): void {
    this.retrieveAllLeagueBoards();
  }

  public retrieveAllLeagueBoards(): void {
    this.isFetching = true;
    this.leagueBoardService()
      .retrieve()
      .then(
        res => {
          this.leagueBoards = res.data;
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

  public prepareRemove(instance: ILeagueBoard): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeLeagueBoard(): void {
    this.leagueBoardService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.leagueBoard.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllLeagueBoards();
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
