/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CupComponent from '@/entities/cup/cup.vue';
import CupClass from '@/entities/cup/cup.component';
import CupService from '@/entities/cup/cup.service';
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
  describe('Cup Management Component', () => {
    let wrapper: Wrapper<CupClass>;
    let comp: CupClass;
    let cupServiceStub: SinonStubbedInstance<CupService>;

    beforeEach(() => {
      cupServiceStub = sinon.createStubInstance<CupService>(CupService);
      cupServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CupClass>(CupComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          cupService: () => cupServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      cupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllCups();
      await comp.$nextTick();

      // THEN
      expect(cupServiceStub.retrieve.called).toBeTruthy();
      expect(comp.cups[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      cupServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(cupServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCup();
      await comp.$nextTick();

      // THEN
      expect(cupServiceStub.delete.called).toBeTruthy();
      expect(cupServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
