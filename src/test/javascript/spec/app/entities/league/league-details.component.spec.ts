/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LeagueDetailComponent from '@/entities/league/league-details.vue';
import LeagueClass from '@/entities/league/league-details.component';
import LeagueService from '@/entities/league/league.service';
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
  describe('League Management Detail Component', () => {
    let wrapper: Wrapper<LeagueClass>;
    let comp: LeagueClass;
    let leagueServiceStub: SinonStubbedInstance<LeagueService>;

    beforeEach(() => {
      leagueServiceStub = sinon.createStubInstance<LeagueService>(LeagueService);

      wrapper = shallowMount<LeagueClass>(LeagueDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { leagueService: () => leagueServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLeague = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        leagueServiceStub.find.resolves(foundLeague);

        // WHEN
        comp.retrieveLeague('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.league).toBe(foundLeague);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLeague = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        leagueServiceStub.find.resolves(foundLeague);

        // WHEN
        comp.beforeRouteEnter({ params: { leagueId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.league).toBe(foundLeague);
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
