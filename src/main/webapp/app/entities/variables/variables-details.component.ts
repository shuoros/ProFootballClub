import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVariables } from '@/shared/model/variables.model';
import VariablesService from './variables.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class VariablesDetails extends Vue {
  @Inject('variablesService') private variablesService: () => VariablesService;
  @Inject('alertService') private alertService: () => AlertService;

  public variables: IVariables = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.variablesId) {
        vm.retrieveVariables(to.params.variablesId);
      }
    });
  }

  public retrieveVariables(variablesId) {
    this.variablesService()
      .find(variablesId)
      .then(res => {
        this.variables = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
