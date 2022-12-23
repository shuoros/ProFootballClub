import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITrain } from '@/shared/model/train.model';
import TrainService from './train.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TrainDetails extends Vue {
  @Inject('trainService') private trainService: () => TrainService;
  @Inject('alertService') private alertService: () => AlertService;

  public train: ITrain = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.trainId) {
        vm.retrieveTrain(to.params.trainId);
      }
    });
  }

  public retrieveTrain(trainId) {
    this.trainService()
      .find(trainId)
      .then(res => {
        this.train = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
