<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cupBoard">
        <h2 class="jh-entity-heading" data-cy="cupBoardDetailsHeading">
          <span v-text="$t('proFootballClubApp.cupBoard.detail.title')">CupBoard</span> {{ cupBoard.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.cupBoard.level')">Level</span>
          </dt>
          <dd>
            <span>{{ cupBoard.level }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.cupBoard.cup')">Cup</span>
          </dt>
          <dd>
            <div v-if="cupBoard.cup">
              <router-link :to="{ name: 'CupView', params: { cupId: cupBoard.cup.id } }">{{ cupBoard.cup.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="cupBoard.id" :to="{ name: 'CupBoardEdit', params: { cupBoardId: cupBoard.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cup-board-details.component.ts"></script>
