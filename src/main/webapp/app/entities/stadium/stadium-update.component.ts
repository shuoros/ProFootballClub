import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import { IStadium, Stadium } from '@/shared/model/stadium.model';
import StadiumService from './stadium.service';
import { Vehicle } from '@/shared/model/enumerations/vehicle.model';
import { Field } from '@/shared/model/enumerations/field.model';
import { Light } from '@/shared/model/enumerations/light.model';
import { Chair } from '@/shared/model/enumerations/chair.model';
import { Assistant } from '@/shared/model/enumerations/assistant.model';
import { BodyBuilding } from '@/shared/model/enumerations/body-building.model';
import { Doctor } from '@/shared/model/enumerations/doctor.model';

const validations: any = {
  stadium: {
    name: {
      required,
    },
    capacity: {
      required,
      numeric,
    },
    ticket: {
      required,
      numeric,
    },
    leader: {},
    vehicle: {},
    field: {},
    light: {},
    chair: {},
    assistant: {},
    bodyBuilding: {},
    doctor: {},
  },
};

@Component({
  validations,
})
export default class StadiumUpdate extends Vue {
  @Inject('stadiumService') private stadiumService: () => StadiumService;
  @Inject('alertService') private alertService: () => AlertService;

  public stadium: IStadium = new Stadium();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public vehicleValues: string[] = Object.keys(Vehicle);
  public fieldValues: string[] = Object.keys(Field);
  public lightValues: string[] = Object.keys(Light);
  public chairValues: string[] = Object.keys(Chair);
  public assistantValues: string[] = Object.keys(Assistant);
  public bodyBuildingValues: string[] = Object.keys(BodyBuilding);
  public doctorValues: string[] = Object.keys(Doctor);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stadiumId) {
        vm.retrieveStadium(to.params.stadiumId);
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
    if (this.stadium.id) {
      this.stadiumService()
        .update(this.stadium)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.stadium.updated', { param: param.id });
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
      this.stadiumService()
        .create(this.stadium)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.stadium.created', { param: param.id });
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
      this.stadium[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.stadium[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.stadium[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.stadium[field] = null;
    }
  }

  public retrieveStadium(stadiumId): void {
    this.stadiumService()
      .find(stadiumId)
      .then(res => {
        res.leader = new Date(res.leader);
        this.stadium = res;
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
