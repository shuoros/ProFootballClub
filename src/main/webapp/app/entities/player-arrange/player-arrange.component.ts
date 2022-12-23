import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPlayerArrange } from '@/shared/model/player-arrange.model';

import PlayerArrangeService from './player-arrange.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PlayerArrange extends Vue {
  @Inject('playerArrangeService') private playerArrangeService: () => PlayerArrangeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public playerArranges: IPlayerArrange[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPlayerArranges();
  }

  public clear(): void {
    this.retrieveAllPlayerArranges();
  }

  public retrieveAllPlayerArranges(): void {
    this.isFetching = true;
    this.playerArrangeService()
      .retrieve()
      .then(
        res => {
          this.playerArranges = res.data;
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

  public prepareRemove(instance: IPlayerArrange): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePlayerArrange(): void {
    this.playerArrangeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.playerArrange.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPlayerArranges();
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
