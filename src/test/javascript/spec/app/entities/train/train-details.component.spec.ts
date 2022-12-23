/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TrainDetailComponent from '@/entities/train/train-details.vue';
import TrainClass from '@/entities/train/train-details.component';
import TrainService from '@/entities/train/train.service';
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
  describe('Train Management Detail Component', () => {
    let wrapper: Wrapper<TrainClass>;
    let comp: TrainClass;
    let trainServiceStub: SinonStubbedInstance<TrainService>;

    beforeEach(() => {
      trainServiceStub = sinon.createStubInstance<TrainService>(TrainService);

      wrapper = shallowMount<TrainClass>(TrainDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { trainService: () => trainServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTrain = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        trainServiceStub.find.resolves(foundTrain);

        // WHEN
        comp.retrieveTrain('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.train).toBe(foundTrain);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTrain = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        trainServiceStub.find.resolves(foundTrain);

        // WHEN
        comp.beforeRouteEnter({ params: { trainId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.train).toBe(foundTrain);
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
