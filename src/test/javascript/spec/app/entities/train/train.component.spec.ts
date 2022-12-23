/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TrainComponent from '@/entities/train/train.vue';
import TrainClass from '@/entities/train/train.component';
import TrainService from '@/entities/train/train.service';
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
  describe('Train Management Component', () => {
    let wrapper: Wrapper<TrainClass>;
    let comp: TrainClass;
    let trainServiceStub: SinonStubbedInstance<TrainService>;

    beforeEach(() => {
      trainServiceStub = sinon.createStubInstance<TrainService>(TrainService);
      trainServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TrainClass>(TrainComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          trainService: () => trainServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      trainServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllTrains();
      await comp.$nextTick();

      // THEN
      expect(trainServiceStub.retrieve.called).toBeTruthy();
      expect(comp.trains[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      trainServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(trainServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTrain();
      await comp.$nextTick();

      // THEN
      expect(trainServiceStub.delete.called).toBeTruthy();
      expect(trainServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
