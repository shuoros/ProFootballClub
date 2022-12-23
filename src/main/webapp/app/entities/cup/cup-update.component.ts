import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import PlayerService from '@/entities/player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import NewsPaperService from '@/entities/news-paper/news-paper.service';
import { INewsPaper } from '@/shared/model/news-paper.model';

import CupBoardService from '@/entities/cup-board/cup-board.service';
import { ICupBoard } from '@/shared/model/cup-board.model';

import { ICup, Cup } from '@/shared/model/cup.model';
import CupService from './cup.service';
import { CupType } from '@/shared/model/enumerations/cup-type.model';

const validations: any = {
  cup: {
    type: {
      required,
    },
    events: {},
    finished: {},
    entrance: {
      required,
      numeric,
    },
    start: {},
  },
};

@Component({
  validations,
})
export default class CupUpdate extends Vue {
  @Inject('cupService') private cupService: () => CupService;
  @Inject('alertService') private alertService: () => AlertService;

  public cup: ICup = new Cup();

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('newsPaperService') private newsPaperService: () => NewsPaperService;

  public newsPapers: INewsPaper[] = [];

  @Inject('cupBoardService') private cupBoardService: () => CupBoardService;

  public cupBoards: ICupBoard[] = [];
  public cupTypeValues: string[] = Object.keys(CupType);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cupId) {
        vm.retrieveCup(to.params.cupId);
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
    if (this.cup.id) {
      this.cupService()
        .update(this.cup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.cup.updated', { param: param.id });
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
      this.cupService()
        .create(this.cup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.cup.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.cup[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.cup[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cup[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.cup[field] = null;
    }
  }

  public retrieveCup(cupId): void {
    this.cupService()
      .find(cupId)
      .then(res => {
        res.start = new Date(res.start);
        this.cup = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
    this.newsPaperService()
      .retrieve()
      .then(res => {
        this.newsPapers = res.data;
      });
    this.cupBoardService()
      .retrieve()
      .then(res => {
        this.cupBoards = res.data;
      });
  }
}
