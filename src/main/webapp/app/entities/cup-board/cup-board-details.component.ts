import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICupBoard } from '@/shared/model/cup-board.model';
import CupBoardService from './cup-board.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CupBoardDetails extends Vue {
  @Inject('cupBoardService') private cupBoardService: () => CupBoardService;
  @Inject('alertService') private alertService: () => AlertService;

  public cupBoard: ICupBoard = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cupBoardId) {
        vm.retrieveCupBoard(to.params.cupBoardId);
      }
    });
  }

  public retrieveCupBoard(cupBoardId) {
    this.cupBoardService()
      .find(cupBoardId)
      .then(res => {
        this.cupBoard = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
