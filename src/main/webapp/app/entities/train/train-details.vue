<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="train">
        <h2 class="jh-entity-heading" data-cy="trainDetailsHeading">
          <span v-text="$t('proFootballClubApp.train.detail.title')">Train</span> {{ train.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.train.training')">Training</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Training.' + train.training)">{{ train.training }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.train.finishes')">Finishes</span>
          </dt>
          <dd>
            <span v-if="train.finishes">{{ $d(Date.parse(train.finishes), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.train.player')">Player</span>
          </dt>
          <dd>
            <div v-if="train.player">
              <router-link :to="{ name: 'PlayerView', params: { playerId: train.player.id } }">{{ train.player.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="train.id" :to="{ name: 'TrainEdit', params: { trainId: train.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./train-details.component.ts"></script>
