/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VariablesDetailComponent from '@/entities/variables/variables-details.vue';
import VariablesClass from '@/entities/variables/variables-details.component';
import VariablesService from '@/entities/variables/variables.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Variables Management Detail Component', () => {
    let wrapper: Wrapper<VariablesClass>;
    let comp: VariablesClass;
    let variablesServiceStub: SinonStubbedInstance<VariablesService>;

    beforeEach(() => {
      variablesServiceStub = sinon.createStubInstance<VariablesService>(VariablesService);

      wrapper = shallowMount<VariablesClass>(VariablesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { variablesService: () => variablesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVariables = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        variablesServiceStub.find.resolves(foundVariables);

        // WHEN
        comp.retrieveVariables('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.variables).toBe(foundVariables);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVariables = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        variablesServiceStub.find.resolves(foundVariables);

        // WHEN
        comp.beforeRouteEnter({ params: { variablesId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.variables).toBe(foundVariables);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
