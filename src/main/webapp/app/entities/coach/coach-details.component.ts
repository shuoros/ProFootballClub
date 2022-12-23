import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICoach } from '@/shared/model/coach.model';
import CoachService from './coach.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CoachDetails extends Vue {
  @Inject('coachService') private coachService: () => CoachService;
  @Inject('alertService') private alertService: () => AlertService;

  public coach: ICoach = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.coachId) {
        vm.retrieveCoach(to.params.coachId);
      }
    });
  }

  public retrieveCoach(coachId) {
    this.coachService()
      .find(coachId)
      .then(res => {
        this.coach = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
