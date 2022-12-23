import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import { ICoach, Coach } from '@/shared/model/coach.model';
import CoachService from './coach.service';
import { Plan } from '@/shared/model/enumerations/plan.model';

const validations: any = {
  coach: {
    name: {
      required,
    },
    banned: {
      required,
    },
    abandoned: {
      required,
    },
    subscribed: {
      required,
    },
    plan: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CoachUpdate extends Vue {
  @Inject('coachService') private coachService: () => CoachService;
  @Inject('alertService') private alertService: () => AlertService;

  public coach: ICoach = new Coach();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public planValues: string[] = Object.keys(Plan);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.coachId) {
        vm.retrieveCoach(to.params.coachId);
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
    if (this.coach.id) {
      this.coachService()
        .update(this.coach)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.coach.updated', { param: param.id });
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
      this.coachService()
        .create(this.coach)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.coach.created', { param: param.id });
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

  public retrieveCoach(coachId): void {
    this.coachService()
      .find(coachId)
      .then(res => {
        this.coach = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
  }
}
