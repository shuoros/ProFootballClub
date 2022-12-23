import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPlayer } from '@/shared/model/player.model';

import PlayerService from './player.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Player extends Vue {
  @Inject('playerService') private playerService: () => PlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public players: IPlayer[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPlayers();
  }

  public clear(): void {
    this.retrieveAllPlayers();
  }

  public retrieveAllPlayers(): void {
    this.isFetching = true;
    this.playerService()
      .retrieve()
      .then(
        res => {
          this.players = res.data;
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

  public prepareRemove(instance: IPlayer): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePlayer(): void {
    this.playerService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.player.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPlayers();
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
