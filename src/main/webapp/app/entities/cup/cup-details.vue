<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cup">
        <h2 class="jh-entity-heading" data-cy="cupDetailsHeading">
          <span v-text="$t('proFootballClubApp.cup.detail.title')">Cup</span> {{ cup.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.cup.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.CupType.' + cup.type)">{{ cup.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.cup.events')">Events</span>
          </dt>
          <dd>
            <span>{{ cup.events }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.cup.finished')">Finished</span>
          </dt>
          <dd>
            <span>{{ cup.finished }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.cup.entrance')">Entrance</span>
          </dt>
          <dd>
            <span>{{ cup.entrance }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.cup.start')">Start</span>
          </dt>
          <dd>
            <span v-if="cup.start">{{ $d(Date.parse(cup.start), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.cup.goalScorer')">Goal Scorer</span>
          </dt>
          <dd>
            <div v-if="cup.goalScorer">
              <router-link :to="{ name: 'PlayerView', params: { playerId: cup.goalScorer.id } }">{{ cup.goalScorer.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.cup.newsPaper')">News Paper</span>
          </dt>
          <dd>
            <div v-if="cup.newsPaper">
              <router-link :to="{ name: 'NewsPaperView', params: { newsPaperId: cup.newsPaper.id } }">{{ cup.newsPaper.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="cup.id" :to="{ name: 'CupEdit', params: { cupId: cup.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cup-details.component.ts"></script>
