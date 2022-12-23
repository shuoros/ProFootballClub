import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILeagueBoard } from '@/shared/model/league-board.model';
import LeagueBoardService from './league-board.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LeagueBoardDetails extends Vue {
  @Inject('leagueBoardService') private leagueBoardService: () => LeagueBoardService;
  @Inject('alertService') private alertService: () => AlertService;

  public leagueBoard: ILeagueBoard = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.leagueBoardId) {
        vm.retrieveLeagueBoard(to.params.leagueBoardId);
      }
    });
  }

  public retrieveLeagueBoard(leagueBoardId) {
    this.leagueBoardService()
      .find(leagueBoardId)
      .then(res => {
        this.leagueBoard = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
