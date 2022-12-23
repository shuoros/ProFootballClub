import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatch } from '@/shared/model/match.model';
import MatchService from './match.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MatchDetails extends Vue {
  @Inject('matchService') private matchService: () => MatchService;
  @Inject('alertService') private alertService: () => AlertService;

  public match: IMatch = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchId) {
        vm.retrieveMatch(to.params.matchId);
      }
    });
  }

  public retrieveMatch(matchId) {
    this.matchService()
      .find(matchId)
      .then(res => {
        this.match = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
