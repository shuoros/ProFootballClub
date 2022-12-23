/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StadiumComponent from '@/entities/stadium/stadium.vue';
import StadiumClass from '@/entities/stadium/stadium.component';
import StadiumService from '@/entities/stadium/stadium.service';
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
  describe('Stadium Management Component', () => {
    let wrapper: Wrapper<StadiumClass>;
    let comp: StadiumClass;
    let stadiumServiceStub: SinonStubbedInstance<StadiumService>;

    beforeEach(() => {
      stadiumServiceStub = sinon.createStubInstance<StadiumService>(StadiumService);
      stadiumServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StadiumClass>(StadiumComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          stadiumService: () => stadiumServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      stadiumServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllStadiums();
      await comp.$nextTick();

      // THEN
      expect(stadiumServiceStub.retrieve.called).toBeTruthy();
      expect(comp.stadiums[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      stadiumServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(stadiumServiceStub.retrieve.callCount).toEqual(1);

      comp.removeStadium();
      await comp.$nextTick();

      // THEN
      expect(stadiumServiceStub.delete.called).toBeTruthy();
      expect(stadiumServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
