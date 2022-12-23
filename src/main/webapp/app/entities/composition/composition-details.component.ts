import { Component, Vue, Inject } from 'vue-property-decorator';

import { IComposition } from '@/shared/model/composition.model';
import CompositionService from './composition.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CompositionDetails extends Vue {
  @Inject('compositionService') private compositionService: () => CompositionService;
  @Inject('alertService') private alertService: () => AlertService;

  public composition: IComposition = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.compositionId) {
        vm.retrieveComposition(to.params.compositionId);
      }
    });
  }

  public retrieveComposition(compositionId) {
    this.compositionService()
      .find(compositionId)
      .then(res => {
        this.composition = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
