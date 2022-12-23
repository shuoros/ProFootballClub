import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import StadiumService from '@/entities/stadium/stadium.service';
import { IStadium } from '@/shared/model/stadium.model';

import PlayerService from '@/entities/player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import CompositionService from '@/entities/composition/composition.service';
import { IComposition } from '@/shared/model/composition.model';

import LeagueBoardService from '@/entities/league-board/league-board.service';
import { ILeagueBoard } from '@/shared/model/league-board.model';

import CoachService from '@/entities/coach/coach.service';
import { ICoach } from '@/shared/model/coach.model';

import { ITeam, Team } from '@/shared/model/team.model';
import TeamService from './team.service';
import { Country } from '@/shared/model/enumerations/country.model';
import { Gender } from '@/shared/model/enumerations/gender.model';

const validations: any = {
  team: {
    name: {
      required,
    },
    homeland: {
      required,
    },
    gender: {
      required,
    },
    money: {
      required,
      numeric,
    },
    points: {
      required,
      numeric,
    },
    matches: {
      required,
      numeric,
    },
    trophies: {
      required,
      numeric,
    },
    readiness: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    spirit: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    fans: {
      required,
      numeric,
    },
    fansSatisfaction: {
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
export default class TeamUpdate extends Vue {
  @Inject('teamService') private teamService: () => TeamService;
  @Inject('alertService') private alertService: () => AlertService;

  public team: ITeam = new Team();

  @Inject('stadiumService') private stadiumService: () => StadiumService;

  public stadiums: IStadium[] = [];

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('compositionService') private compositionService: () => CompositionService;

  public compositions: IComposition[] = [];

  public teams: ITeam[] = [];

  @Inject('leagueBoardService') private leagueBoardService: () => LeagueBoardService;

  public leagueBoards: ILeagueBoard[] = [];

  @Inject('coachService') private coachService: () => CoachService;

  public coaches: ICoach[] = [];
  public countryValues: string[] = Object.keys(Country);
  public genderValues: string[] = Object.keys(Gender);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamId) {
        vm.retrieveTeam(to.params.teamId);
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
    if (this.team.id) {
      this.teamService()
        .update(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.team.updated', { param: param.id });
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
      this.teamService()
        .create(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.team.created', { param: param.id });
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

  public retrieveTeam(teamId): void {
    this.teamService()
      .find(teamId)
      .then(res => {
        this.team = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.stadiumService()
      .retrieve()
      .then(res => {
        this.stadiums = res.data;
      });
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
    this.compositionService()
      .retrieve()
      .then(res => {
        this.compositions = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.leagueBoardService()
      .retrieve()
      .then(res => {
        this.leagueBoards = res.data;
      });
    this.coachService()
      .retrieve()
      .then(res => {
        this.coaches = res.data;
      });
  }
}
