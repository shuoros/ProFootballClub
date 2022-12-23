import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import { IPlayer, Player } from '@/shared/model/player.model';
import PlayerService from './player.service';
import { Gender } from '@/shared/model/enumerations/gender.model';
import { Country } from '@/shared/model/enumerations/country.model';
import { PlayerStatus } from '@/shared/model/enumerations/player-status.model';
import { Post } from '@/shared/model/enumerations/post.model';

const validations: any = {
  player: {
    firstName: {
      required,
    },
    lastName: {
      required,
    },
    gender: {
      required,
    },
    country: {
      required,
    },
    age: {
      required,
      numeric,
    },
    status: {
      required,
    },
    post: {
      required,
    },
    totalPower: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(256),
    },
    goalkeeping: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    defence: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    tackling: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    passing: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    teamSkill: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    ballSkill: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    shooting: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    longShooting: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    dribbling: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    technique: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    confidence: {
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
export default class PlayerUpdate extends Vue {
  @Inject('playerService') private playerService: () => PlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  public player: IPlayer = new Player();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public genderValues: string[] = Object.keys(Gender);
  public countryValues: string[] = Object.keys(Country);
  public playerStatusValues: string[] = Object.keys(PlayerStatus);
  public postValues: string[] = Object.keys(Post);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerId) {
        vm.retrievePlayer(to.params.playerId);
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
    if (this.player.id) {
      this.playerService()
        .update(this.player)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.player.updated', { param: param.id });
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
      this.playerService()
        .create(this.player)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.player.created', { param: param.id });
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

  public retrievePlayer(playerId): void {
    this.playerService()
      .find(playerId)
      .then(res => {
        this.player = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
  }
}
