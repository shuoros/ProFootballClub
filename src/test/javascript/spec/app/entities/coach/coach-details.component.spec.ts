/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CoachDetailComponent from '@/entities/coach/coach-details.vue';
import CoachClass from '@/entities/coach/coach-details.component';
import CoachService from '@/entities/coach/coach.service';
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
  describe('Coach Management Detail Component', () => {
    let wrapper: Wrapper<CoachClass>;
    let comp: CoachClass;
    let coachServiceStub: SinonStubbedInstance<CoachService>;

    beforeEach(() => {
      coachServiceStub = sinon.createStubInstance<CoachService>(CoachService);

      wrapper = shallowMount<CoachClass>(CoachDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { coachService: () => coachServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCoach = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        coachServiceStub.find.resolves(foundCoach);

        // WHEN
        comp.retrieveCoach('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.coach).toBe(foundCoach);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCoach = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        coachServiceStub.find.resolves(foundCoach);

        // WHEN
        comp.beforeRouteEnter({ params: { coachId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.coach).toBe(foundCoach);
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
