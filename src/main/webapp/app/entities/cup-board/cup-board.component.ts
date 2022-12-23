import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICupBoard } from '@/shared/model/cup-board.model';

import CupBoardService from './cup-board.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CupBoard extends Vue {
  @Inject('cupBoardService') private cupBoardService: () => CupBoardService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public cupBoards: ICupBoard[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCupBoards();
  }

  public clear(): void {
    this.retrieveAllCupBoards();
  }

  public retrieveAllCupBoards(): void {
    this.isFetching = true;
    this.cupBoardService()
      .retrieve()
      .then(
        res => {
          this.cupBoards = res.data;
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

  public prepareRemove(instance: ICupBoard): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCupBoard(): void {
    this.cupBoardService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.cupBoard.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCupBoards();
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
