/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import StadiumDetailComponent from '@/entities/stadium/stadium-details.vue';
import StadiumClass from '@/entities/stadium/stadium-details.component';
import StadiumService from '@/entities/stadium/stadium.service';
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
  describe('Stadium Management Detail Component', () => {
    let wrapper: Wrapper<StadiumClass>;
    let comp: StadiumClass;
    let stadiumServiceStub: SinonStubbedInstance<StadiumService>;

    beforeEach(() => {
      stadiumServiceStub = sinon.createStubInstance<StadiumService>(StadiumService);

      wrapper = shallowMount<StadiumClass>(StadiumDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { stadiumService: () => stadiumServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStadium = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        stadiumServiceStub.find.resolves(foundStadium);

        // WHEN
        comp.retrieveStadium('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.stadium).toBe(foundStadium);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStadium = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        stadiumServiceStub.find.resolves(foundStadium);

        // WHEN
        comp.beforeRouteEnter({ params: { stadiumId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.stadium).toBe(foundStadium);
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
