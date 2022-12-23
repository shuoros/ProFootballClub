/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import VariablesComponent from '@/entities/variables/variables.vue';
import VariablesClass from '@/entities/variables/variables.component';
import VariablesService from '@/entities/variables/variables.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Variables Management Component', () => {
    let wrapper: Wrapper<VariablesClass>;
    let comp: VariablesClass;
    let variablesServiceStub: SinonStubbedInstance<VariablesService>;

    beforeEach(() => {
      variablesServiceStub = sinon.createStubInstance<VariablesService>(VariablesService);
      variablesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VariablesClass>(VariablesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          variablesService: () => variablesServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      variablesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllVariabless();
      await comp.$nextTick();

      // THEN
      expect(variablesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.variables[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      variablesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(variablesServiceStub.retrieve.callCount).toEqual(1);

      comp.removeVariables();
      await comp.$nextTick();

      // THEN
      expect(variablesServiceStub.delete.called).toBeTruthy();
      expect(variablesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
