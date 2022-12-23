/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MatchDetailComponent from '@/entities/match/match-details.vue';
import MatchClass from '@/entities/match/match-details.component';
import MatchService from '@/entities/match/match.service';
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
  describe('Match Management Detail Component', () => {
    let wrapper: Wrapper<MatchClass>;
    let comp: MatchClass;
    let matchServiceStub: SinonStubbedInstance<MatchService>;

    beforeEach(() => {
      matchServiceStub = sinon.createStubInstance<MatchService>(MatchService);

      wrapper = shallowMount<MatchClass>(MatchDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { matchService: () => matchServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatch = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        matchServiceStub.find.resolves(foundMatch);

        // WHEN
        comp.retrieveMatch('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.match).toBe(foundMatch);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMatch = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        matchServiceStub.find.resolves(foundMatch);

        // WHEN
        comp.beforeRouteEnter({ params: { matchId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.match).toBe(foundMatch);
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
