/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PlayerArrangeDetailComponent from '@/entities/player-arrange/player-arrange-details.vue';
import PlayerArrangeClass from '@/entities/player-arrange/player-arrange-details.component';
import PlayerArrangeService from '@/entities/player-arrange/player-arrange.service';
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
  describe('PlayerArrange Management Detail Component', () => {
    let wrapper: Wrapper<PlayerArrangeClass>;
    let comp: PlayerArrangeClass;
    let playerArrangeServiceStub: SinonStubbedInstance<PlayerArrangeService>;

    beforeEach(() => {
      playerArrangeServiceStub = sinon.createStubInstance<PlayerArrangeService>(PlayerArrangeService);

      wrapper = shallowMount<PlayerArrangeClass>(PlayerArrangeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { playerArrangeService: () => playerArrangeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPlayerArrange = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        playerArrangeServiceStub.find.resolves(foundPlayerArrange);

        // WHEN
        comp.retrievePlayerArrange('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.playerArrange).toBe(foundPlayerArrange);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPlayerArrange = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        playerArrangeServiceStub.find.resolves(foundPlayerArrange);

        // WHEN
        comp.beforeRouteEnter({ params: { playerArrangeId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.playerArrange).toBe(foundPlayerArrange);
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
