import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILeague } from '@/shared/model/league.model';
import LeagueService from './league.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LeagueDetails extends Vue {
  @Inject('leagueService') private leagueService: () => LeagueService;
  @Inject('alertService') private alertService: () => AlertService;

  public league: ILeague = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.leagueId) {
        vm.retrieveLeague(to.params.leagueId);
      }
    });
  }

  public retrieveLeague(leagueId) {
    this.leagueService()
      .find(leagueId)
      .then(res => {
        this.league = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
