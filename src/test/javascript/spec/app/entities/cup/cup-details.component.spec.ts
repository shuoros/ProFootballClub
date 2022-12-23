/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CupDetailComponent from '@/entities/cup/cup-details.vue';
import CupClass from '@/entities/cup/cup-details.component';
import CupService from '@/entities/cup/cup.service';
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
  describe('Cup Management Detail Component', () => {
    let wrapper: Wrapper<CupClass>;
    let comp: CupClass;
    let cupServiceStub: SinonStubbedInstance<CupService>;

    beforeEach(() => {
      cupServiceStub = sinon.createStubInstance<CupService>(CupService);

      wrapper = shallowMount<CupClass>(CupDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { cupService: () => cupServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        cupServiceStub.find.resolves(foundCup);

        // WHEN
        comp.retrieveCup('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.cup).toBe(foundCup);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        cupServiceStub.find.resolves(foundCup);

        // WHEN
        comp.beforeRouteEnter({ params: { cupId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cup).toBe(foundCup);
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
