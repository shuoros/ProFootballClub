import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import PlayerService from '@/entities/player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import PlayerArrangeService from '@/entities/player-arrange/player-arrange.service';
import { IPlayerArrange } from '@/shared/model/player-arrange.model';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import { IComposition, Composition } from '@/shared/model/composition.model';
import CompositionService from './composition.service';
import { Arrange } from '@/shared/model/enumerations/arrange.model';
import { Strategy } from '@/shared/model/enumerations/strategy.model';

const validations: any = {
  composition: {
    d3fault: {
      required,
    },
    arrange: {
      required,
    },
    strategy: {
      required,
    },
    defence: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    shortPass: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    violence: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
  },
};

@Component({
  validations,
})
export default class CompositionUpdate extends Vue {
  @Inject('compositionService') private compositionService: () => CompositionService;
  @Inject('alertService') private alertService: () => AlertService;

  public composition: IComposition = new Composition();

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('playerArrangeService') private playerArrangeService: () => PlayerArrangeService;

  public playerArranges: IPlayerArrange[] = [];

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public arrangeValues: string[] = Object.keys(Arrange);
  public strategyValues: string[] = Object.keys(Strategy);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.compositionId) {
        vm.retrieveComposition(to.params.compositionId);
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
    if (this.composition.id) {
      this.compositionService()
        .update(this.composition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.composition.updated', { param: param.id });
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
      this.compositionService()
        .create(this.composition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.composition.created', { param: param.id });
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

  public retrieveComposition(compositionId): void {
    this.compositionService()
      .find(compositionId)
      .then(res => {
        this.composition = res;
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
    this.playerArrangeService()
      .retrieve()
      .then(res => {
        this.playerArranges = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
  }
}
