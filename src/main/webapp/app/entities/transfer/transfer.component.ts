import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITransfer } from '@/shared/model/transfer.model';

import TransferService from './transfer.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Transfer extends Vue {
  @Inject('transferService') private transferService: () => TransferService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public transfers: ITransfer[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTransfers();
  }

  public clear(): void {
    this.retrieveAllTransfers();
  }

  public retrieveAllTransfers(): void {
    this.isFetching = true;
    this.transferService()
      .retrieve()
      .then(
        res => {
          this.transfers = res.data;
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

  public prepareRemove(instance: ITransfer): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTransfer(): void {
    this.transferService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.transfer.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTransfers();
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
