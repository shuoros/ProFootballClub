/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PlayerUpdateComponent from '@/entities/player/player-update.vue';
import PlayerClass from '@/entities/player/player-update.component';
import PlayerService from '@/entities/player/player.service';

import TeamService from '@/entities/team/team.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Player Management Update Component', () => {
    let wrapper: Wrapper<PlayerClass>;
    let comp: PlayerClass;
    let playerServiceStub: SinonStubbedInstance<PlayerService>;

    beforeEach(() => {
      playerServiceStub = sinon.createStubInstance<PlayerService>(PlayerService);

      wrapper = shallowMount<PlayerClass>(PlayerUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          playerService: () => playerServiceStub,
          alertService: () => new AlertService(),

          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        comp.player = entity;
        playerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.player = entity;
        playerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPlayer = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        playerServiceStub.find.resolves(foundPlayer);
        playerServiceStub.retrieve.resolves([foundPlayer]);

        // WHEN
        comp.beforeRouteEnter({ params: { playerId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.player).toBe(foundPlayer);
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
