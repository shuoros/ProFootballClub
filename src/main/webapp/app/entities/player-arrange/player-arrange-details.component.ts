import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPlayerArrange } from '@/shared/model/player-arrange.model';
import PlayerArrangeService from './player-arrange.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PlayerArrangeDetails extends Vue {
  @Inject('playerArrangeService') private playerArrangeService: () => PlayerArrangeService;
  @Inject('alertService') private alertService: () => AlertService;

  public playerArrange: IPlayerArrange = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerArrangeId) {
        vm.retrievePlayerArrange(to.params.playerArrangeId);
      }
    });
  }

  public retrievePlayerArrange(playerArrangeId) {
    this.playerArrangeService()
      .find(playerArrangeId)
      .then(res => {
        this.playerArrange = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
