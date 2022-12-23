import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStadium } from '@/shared/model/stadium.model';
import StadiumService from './stadium.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class StadiumDetails extends Vue {
  @Inject('stadiumService') private stadiumService: () => StadiumService;
  @Inject('alertService') private alertService: () => AlertService;

  public stadium: IStadium = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stadiumId) {
        vm.retrieveStadium(to.params.stadiumId);
      }
    });
  }

  public retrieveStadium(stadiumId) {
    this.stadiumService()
      .find(stadiumId)
      .then(res => {
        this.stadium = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
