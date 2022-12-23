/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PlayerArrangeComponent from '@/entities/player-arrange/player-arrange.vue';
import PlayerArrangeClass from '@/entities/player-arrange/player-arrange.component';
import PlayerArrangeService from '@/entities/player-arrange/player-arrange.service';
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
  describe('PlayerArrange Management Component', () => {
    let wrapper: Wrapper<PlayerArrangeClass>;
    let comp: PlayerArrangeClass;
    let playerArrangeServiceStub: SinonStubbedInstance<PlayerArrangeService>;

    beforeEach(() => {
      playerArrangeServiceStub = sinon.createStubInstance<PlayerArrangeService>(PlayerArrangeService);
      playerArrangeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PlayerArrangeClass>(PlayerArrangeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          playerArrangeService: () => playerArrangeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      playerArrangeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllPlayerArranges();
      await comp.$nextTick();

      // THEN
      expect(playerArrangeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.playerArranges[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      playerArrangeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(playerArrangeServiceStub.retrieve.callCount).toEqual(1);

      comp.removePlayerArrange();
      await comp.$nextTick();

      // THEN
      expect(playerArrangeServiceStub.delete.called).toBeTruthy();
      expect(playerArrangeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
