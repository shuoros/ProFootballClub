/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LeagueBoardDetailComponent from '@/entities/league-board/league-board-details.vue';
import LeagueBoardClass from '@/entities/league-board/league-board-details.component';
import LeagueBoardService from '@/entities/league-board/league-board.service';
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
  describe('LeagueBoard Management Detail Component', () => {
    let wrapper: Wrapper<LeagueBoardClass>;
    let comp: LeagueBoardClass;
    let leagueBoardServiceStub: SinonStubbedInstance<LeagueBoardService>;

    beforeEach(() => {
      leagueBoardServiceStub = sinon.createStubInstance<LeagueBoardService>(LeagueBoardService);

      wrapper = shallowMount<LeagueBoardClass>(LeagueBoardDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { leagueBoardService: () => leagueBoardServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLeagueBoard = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        leagueBoardServiceStub.find.resolves(foundLeagueBoard);

        // WHEN
        comp.retrieveLeagueBoard('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.leagueBoard).toBe(foundLeagueBoard);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLeagueBoard = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        leagueBoardServiceStub.find.resolves(foundLeagueBoard);

        // WHEN
        comp.beforeRouteEnter({ params: { leagueBoardId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.leagueBoard).toBe(foundLeagueBoard);
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
