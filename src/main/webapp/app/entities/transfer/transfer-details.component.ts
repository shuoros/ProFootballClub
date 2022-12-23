import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITransfer } from '@/shared/model/transfer.model';
import TransferService from './transfer.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TransferDetails extends Vue {
  @Inject('transferService') private transferService: () => TransferService;
  @Inject('alertService') private alertService: () => AlertService;

  public transfer: ITransfer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transferId) {
        vm.retrieveTransfer(to.params.transferId);
      }
    });
  }

  public retrieveTransfer(transferId) {
    this.transferService()
      .find(transferId)
      .then(res => {
        this.transfer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
