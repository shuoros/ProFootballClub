/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import PlayerService from '@/entities/player/player.service';
import { Player } from '@/shared/model/player.model';
import { Gender } from '@/shared/model/enumerations/gender.model';
import { Country } from '@/shared/model/enumerations/country.model';
import { PlayerStatus } from '@/shared/model/enumerations/player-status.model';
import { Post } from '@/shared/model/enumerations/post.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Player Service', () => {
    let service: PlayerService;
    let elemDefault;

    beforeEach(() => {
      service = new PlayerService();
      elemDefault = new Player(
        '9fec3727-3421-4967-b213-ba36557ca194',
        'AAAAAAA',
        'AAAAAAA',
        Gender.DIVERSE,
        Country.MULTY_CULTI,
        0,
        PlayerStatus.NOT_READY,
        Post.GK,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('9fec3727-3421-4967-b213-ba36557ca194').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('9fec3727-3421-4967-b213-ba36557ca194')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Player', async () => {
        const returnedFromService = Object.assign(
          {
            id: '9fec3727-3421-4967-b213-ba36557ca194',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Player', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Player', async () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            gender: 'BBBBBB',
            country: 'BBBBBB',
            age: 1,
            status: 'BBBBBB',
            post: 'BBBBBB',
            totalPower: 1,
            goalkeeping: 1,
            defence: 1,
            tackling: 1,
            passing: 1,
            teamSkill: 1,
            ballSkill: 1,
            shooting: 1,
            longShooting: 1,
            dribbling: 1,
            technique: 1,
            confidence: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Player', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Player', async () => {
        const patchObject = Object.assign(
          {
            age: 1,
            status: 'BBBBBB',
            defence: 1,
            tackling: 1,
            passing: 1,
            shooting: 1,
            technique: 1,
            confidence: 1,
          },
          new Player()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Player', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Player', async () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            gender: 'BBBBBB',
            country: 'BBBBBB',
            age: 1,
            status: 'BBBBBB',
            post: 'BBBBBB',
            totalPower: 1,
            goalkeeping: 1,
            defence: 1,
            tackling: 1,
            passing: 1,
            teamSkill: 1,
            ballSkill: 1,
            shooting: 1,
            longShooting: 1,
            dribbling: 1,
            technique: 1,
            confidence: 1,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Player', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Player', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('9fec3727-3421-4967-b213-ba36557ca194').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Player', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('9fec3727-3421-4967-b213-ba36557ca194')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
