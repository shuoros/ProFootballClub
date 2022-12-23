import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICup } from '@/shared/model/cup.model';
import CupService from './cup.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CupDetails extends Vue {
  @Inject('cupService') private cupService: () => CupService;
  @Inject('alertService') private alertService: () => AlertService;

  public cup: ICup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cupId) {
        vm.retrieveCup(to.params.cupId);
      }
    });
  }

  public retrieveCup(cupId) {
    this.cupService()
      .find(cupId)
      .then(res => {
        this.cup = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
