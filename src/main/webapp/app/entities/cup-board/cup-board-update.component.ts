import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import MatchService from '@/entities/match/match.service';
import { IMatch } from '@/shared/model/match.model';

import CupService from '@/entities/cup/cup.service';
import { ICup } from '@/shared/model/cup.model';

import { ICupBoard, CupBoard } from '@/shared/model/cup-board.model';
import CupBoardService from './cup-board.service';

const validations: any = {
  cupBoard: {
    level: {
      required,
      numeric,
      min: minValue(1),
      max: maxValue(12),
    },
  },
};

@Component({
  validations,
})
export default class CupBoardUpdate extends Vue {
  @Inject('cupBoardService') private cupBoardService: () => CupBoardService;
  @Inject('alertService') private alertService: () => AlertService;

  public cupBoard: ICupBoard = new CupBoard();

  @Inject('matchService') private matchService: () => MatchService;

  public matches: IMatch[] = [];

  @Inject('cupService') private cupService: () => CupService;

  public cups: ICup[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cupBoardId) {
        vm.retrieveCupBoard(to.params.cupBoardId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.cupBoard.id) {
      this.cupBoardService()
        .update(this.cupBoard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.cupBoard.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.cupBoardService()
        .create(this.cupBoard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.cupBoard.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveCupBoard(cupBoardId): void {
    this.cupBoardService()
      .find(cupBoardId)
      .then(res => {
        this.cupBoard = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.matchService()
      .retrieve()
      .then(res => {
        this.matches = res.data;
      });
    this.cupService()
      .retrieve()
      .then(res => {
        this.cups = res.data;
      });
  }
}
